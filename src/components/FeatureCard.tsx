
import React from 'react';
import { LucideIcon } from 'lucide-react';
import { cn } from '@/lib/utils';

interface FeatureCardProps {
  title: string;
  icon: LucideIcon;
  variant: 'primary' | 'secondary';
  onClick?: () => void;
}

const FeatureCard: React.FC<FeatureCardProps> = ({ 
  title, 
  icon: Icon, 
  variant = 'primary',
  onClick 
}) => {
  return (
    <div 
      className={cn(
        "feature-card animate-scale-in cursor-pointer", 
        variant
      )}
      onClick={onClick}
    >
      <div className={cn(
        "w-14 h-14 mb-3 rounded-full flex items-center justify-center",
        variant === 'primary' ? 'bg-blue-100 text-vagacerta-lightBlue' : 'bg-orange-100 text-vagacerta-orange'
      )}>
        <Icon size={28} />
      </div>
      <h3 className="font-semibold text-lg">{title}</h3>
    </div>
  );
};

export default FeatureCard;
